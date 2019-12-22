package com.datatech.restapi

trait ModelBase[M,E] {
  def to: M => E
  def from: E => M
}
