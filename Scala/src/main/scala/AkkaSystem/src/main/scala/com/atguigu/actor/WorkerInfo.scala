package com.atguigu.actor

/**
  * Created by wuyufei on 03/09/2017.
  */
class WorkerInfo(val id : String, val workerHost : String, val memory : String, val cores : String) {

  var lastHeartbeat : Long = System.currentTimeMillis()

  override def toString = s"WorkerInfo($id, $workerHost, $memory, $cores)"

}
