package com.innovez.learn
// Invoke method dynamically.
String str = "hello"
String methodName = "toUpperCase"
def methodOfIterest = String.metaClass.getMetaMethod(methodName)
println methodOfIterest.invoke(str);

// Using respondsTo to check whether object can handle method call
print 'Can response to toUpperCase(String) method call? '
println String.metaClass.respondsTo(str, 'toUpperCase') ? 'yes' : 'no'
print 'Can response to compareTo(String) method call? '
println String.metaClass.respondsTo(str, 'compareTo', 'test') ? 'yes' : 'no'
print 'Can response to toUpperCase(int) method call? '
println String.metaClass.respondsTo(str, 'toUpperCase', 5) ? 'yes' : 'no'

/**
 * Intercepting method call using GroovyInterceptable. 
 * All call to this object type method will be routed to invokeMethod first.
 * 
 * @author zakyalvan
 */
class Car implements GroovyInterceptable {
	def check() {
		System.out.println "Check called..."
	}
	def start() {
		System.out.println "Start called..."
	}
	def drive() {
		System.out.println "Drive called..."
	}
	
	Object invokeMethod(String methodName, args) {
		// See what happen if we use println directly
		System.out.print "Call to ${methodName} intercepted..."
		if(!methodName.equals("check")) {
			System.out.print "Running filter..."
			
			// What happen if we call check() directly?
			Car.metaClass.invokeMethod(this, "check")
			//this.check();
		}
		
		def validMethod = Car.metaClass.getMetaMethod(methodName, args)
		if(validMethod != null) {
			validMethod.invoke(this, args)
		}
		else {
			Car.metaClass.invokeMethod(this, methodName, args)
		}
	}
}
println 'Intercept method call using invokeMethod of object implementing GroovyInterceptable'
def car = new Car()
car.start()
car.drive()
car.check()
//car.speed();

// Intercepting method call using invokeMethod of meta class
class OtherCar {
	def check() {
		System.out.println 'Check called...'
	}
	def start() {
		System.out.println 'Start called...'
	}
	def drive() {
		System.out.println 'Drive called...'
	}
}
OtherCar.metaClass.invokeMethod = {String method, Object args ->
	System.out.print "Call to ${method} intercepted..."
	if(!method.equals('check')) {
		System.out.print 'Running filter...'
		OtherCar.metaClass.getMetaMethod('check').invoke(delegate, null)
	}
	
	def validMethod = OtherCar.metaClass.getMetaMethod(method, args);
	if(validMethod != null) {
		validMethod.invoke(delegate, args)
	}
	else {
		OtherCar.metaClass.invokeMissingMethod(delegate, method, args)
	}
}
println "Intercept using invokeMethod of POGO metaClass"
OtherCar otherCar = new OtherCar();
otherCar.drive();
otherCar.start();
otherCar.check();

try {
	otherCar.speedup()
}
catch(Exception e) {
	e.printStackTrace()
}


/**
 * Just want to know, which invokeMethod invoked first, metaclass or interceptable.
 * 
 * @author zakyalvan
 */
class SimpleCar implements GroovyInterceptable {
	def start() {
		System.out.println 'Car started...'
	}
	
	def invokeMethod(String name, Object args) {
		System.out.println "invokeMethod on GroovyInterceptable invoked for method ${name}..."
		metaClass.getMetaMethod(name, args).invoke(this, args)
	}
}
SimpleCar.metaClass.invokeMethod = {String name, Object args ->
	System.out.println "invokeMethod on metaclass invoked for method ${name}..."
	delegate.metaClass.invokeMethod(name, args)
}
def simpleCar = new SimpleCar()
simpleCar.start()