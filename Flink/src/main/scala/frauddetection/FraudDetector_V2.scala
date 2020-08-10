package frauddetection

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.typeinfo.Types
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.util.Collector
import org.apache.flink.walkthrough.common.entity.{Alert, Transaction}

/**
 * 欺诈检测程序 2.0
 * 状态 + 时间
 *
 * @since 2020.08.10
 *
 */
object FraudDetector_V2 {

}

class FraudDetector_V2 extends KeyedProcessFunction[Long, Transaction, Alert]{

  @transient private var flagState: ValueState[java.lang.Boolean] = _
  @transient private var timerState: ValueState[java.lang.Long] = _

  /**
   * 状态在使用之前需要先被注册，使用open进行注册
   */
  @throws[Exception]
  override def open(parameters: Configuration):Unit = {
    val flagDescriptor = new ValueStateDescriptor("flag", Types.BOOLEAN)
    flagState = getRuntimeContext.getState(flagDescriptor)

    val timeDescriptor = new ValueStateDescriptor("time-state", Types.LONG)
    timerState = getRuntimeContext.getState(timeDescriptor)
  }

  @throws[Exception]
  override def processElement(
    transaction: Transaction,
    context: KeyedProcessFunction[Long, Transaction, Alert]#Context,
    collector: Collector[Alert]): Unit = {

    // 获取当前key的状态
    val lastTransactionWasSmall = flagState.value

    if(lastTransactionWasSmall != null){
      if(transaction.getAmount > FraudDetector.LARGE_AMOUNT){
        val alert = new Alert
        alert.setId(transaction.getAccountId)
        collector.collect(alert)
      }
      cleanup(context)
    }

    if(transaction.getAmount < FraudDetector.SMALL_AMOUNT){
      flagState.update(true)

      /**
       *  设置 timer和 timer state
       *  定时器服务：
       *  1) 可以用于查询当前时间、注册定时器、删除定时器
       *  2) 可以在标记状态被设置时，也设置一个当前时间一分钟后触发的定时器，同时将时间保存到timerState状态中
       */
      val timer = context.timerService.currentProcessingTime + FraudDetector.ONE_MINUTE
      context.timerService.registerProcessingTimeTimer(timer)
      timerState.update(timer)
    }
  }

  /**
   * 定时器触发时，会调用该方法，重写该方法实现自定义的重置状态的回调逻辑
   */
  override def onTimer(
    timestamp: Long,
    context: KeyedProcessFunction[Long, Transaction, Alert]#OnTimerContext,
    out: Collector[Alert]): Unit = {
    // 1分钟之后清除状态
    timerState.clear
    flagState.clear
  }

  /**
   * 状态清理
   */
  @throws[Exception]
  private def cleanup(context: KeyedProcessFunction[Long, Transaction, Alert]#Context): Unit ={
    val timer = timerState.value
    // 删除定时器
    context.timerService.deleteProcessingTimeTimer(timer)

    // 清理所有的状态
    timerState.clear
    flagState.clear
  }
}
