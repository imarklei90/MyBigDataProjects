package com.atguigu.actor

/**
  * Created by wuyufei on 03/09/2017.
  */
case class RegisterWorker(val id : String, val workerHost : String, val memory : String, val cores : String)

case class HeartBeat(val workid : String)

case class CheckOfTimeOutWorker()

case class RegisteredWorker(val workerHost : String)

case class SendHeartBeat()