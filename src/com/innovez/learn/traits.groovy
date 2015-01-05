/**
 * <p>
 * This script is all about new groovy feature i.e trait
 * 
 * <p>
 * Base on groovy decs (http://groovy-lang.org/docs/groovy-2.3.0/html/documentation/core-traits.html).
 * 
 * <p>
 * Traits are a a structural construct of the language which allow:
 * <ul>
 * <li>composition of behaviors
 * <li>runtime implementation of interfaces
 * <li>behavior overriding
 * <li>compatibility with static type checking/compilation
 * <li>They can be seen as interfaces carrying both default implementations and state. A trait is defined using the trait keyword
 * </ul>
 */
trait Introspector {
	String whoAmI() {
		println "${this.class.simpleName}"
	}
}

class Foo implements Introspector {}
class Bar implements Introspector {}

def foo = new Foo()
def bar = new Bar()

foo.whoAmI()
bar.whoAmI()