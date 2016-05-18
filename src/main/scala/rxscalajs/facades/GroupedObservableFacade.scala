package rxscalajs.facades

import rxscalajs.Subscription
import rxscalajs.Subject
import scala.scalajs.js

/**
* Created by Luka on 18.05.2016.
*/
@js.native
class GroupedObservableFacade[K,T] protected() extends ObservableFacade[T] {
  def this(key: K, groupSubject: Subject[T], refCountSubscription: Subscription) = this()
}
