package frauddetection

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.typeinfo.Types
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.util.Collector
import org.apache.flink.walkthrough.common.entity.{Alert, Transaction}

/**
 * 欺诈交易检测业务逻辑
 *
 * @since 2020.08.10
 */
object FraudDetector {
  val SMALL_AMOUNT: Double = 1.00
  val LARGE_AMOUNT: Double = 500.00
  val ONE_MINUTE: Long = 60 * 1000L
}

// KeyedProcessFunction接口的实现
@SerialVersionUID(1L)
class FraudDetector extends KeyedProcessFunction[Long, Transaction, Alert]{

  /** ValueState:
   * 1) 是一个包装类，类似于Java中的AtomicReference和AtomicLong
   * 2) Flink中最基础的状态类型,能够为被其封装的变量添加容错能力的类型
   * 3) 是一种keyed state， 只能被用于keyed context提供的operator中
   * 4) 一个operator中的keyed state的作用域默认是属于它所属的key的
   */
  private var flagState: ValueState[java.lang.Boolean] = _

  /**
   * 状态在使用之前需要先被注册，使用open进行注册
   */
  @throws[Exception]
  override def open(parameters: Configuration):Unit = {
    val flagDescriptor = new ValueStateDescriptor("flag", Types.BOOLEAN) // 用于创建ValueState, 包含了Flink如何管理变量的一些元数据信息
    flagState = getRuntimeContext.getState(flagDescriptor)
  }

  /**
   * 使用状态标记类追踪可能的欺诈交易行为
   */
  @throws[Exception]
  override def processElement(
    transaction: Transaction,
    context: KeyedProcessFunction[Long, Transaction, Alert]#Context,
    collector: Collector[Alert]): Unit = {

    /**
     * ValueState操作的三个方法
     * 1) value 获取状态值
     * 2) update 更新状态值
     * 3) clear 清空状态值
     *
     * ValueState的三种状态
     * 1) unset(null)
     * 2) true
     * 3) false
     */

    // 获取当前key的当前状态
    val lastTransactionWasSmall = flagState.value

    // 检测 flag 是否已经设置
    if(lastTransactionWasSmall != null){
      if (transaction.getAmount > FraudDetector.LARGE_AMOUNT){
        val alert = new Alert
        alert.setId(transaction.getAccountId)
        // 输出alert信息
        collector.collect(alert)
      }
      // 清空状态
      flagState.clear()
    }

    if(transaction.getAmount < FraudDetector.SMALL_AMOUNT){
      // 更新状态
      flagState.update(true)
    }

  }
}
