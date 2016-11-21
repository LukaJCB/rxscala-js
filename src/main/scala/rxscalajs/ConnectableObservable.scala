package rxscalajs

import rxscalajs.facade.ConnectableObservableFacade
import rxscalajs.subscription.Subscription

class ConnectableObservable[+T] protected[rxscalajs](val inner: ConnectableObservableFacade[T]) {
  def connect: Subscription = inner.connect
  def refCount: Observable[T] = new Observable[T](inner.refCount)
}
