package rxscalajs.facade
import rxscalajs.Subscription

import scala.scalajs.js





@js.native
class ConnectableObservableFacade[T] protected() extends ObservableFacade[T] {
  def this(source: ObservableFacade[T], subjectFactory: js.Function0[SubjectFacade[T]]) = this()
  def connect(): Subscription = js.native
  def refCount(): ObservableFacade[T] = js.native
}
