import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
trait Scheduler extends js.Object {
        def now(): Double = js.native
def schedule[T](work: js.Function1[T, Subscription | Unit], delay: Double = ???, state: T = ???): Subscription = js.native
def flush(): Unit = js.native
var active: Boolean = js.native
var actions: js.Array[Action[js.Any]] = js.native
var scheduledId: Double = js.native
}

}
