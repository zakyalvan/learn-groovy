def people = ['Ferdinan', 'Suwito', 'Salman', 'Iqbal', 'Moko', 'Zaky'];

// Using special var it
people.each({
	println "Hi, ${it}"
});

Closure greeter = {name -> println "Hello, ${name}!"};
people.each greeter;

Object numberMap = ['one' : 'Satu', 'two' : 'Dua', 'three' : 'Tiga'];
numberMap.each {item ->
	def itemClassName = item.getClass().getName()
	println "Item class name : ${itemClassName}, key : ${item.key}, value : ${item.value}"
}

/**
 * Groovy has a special handling for methods whose last parameter is a closure. When
 * invoking these methods, the closure can be defined anonymously after the method
 * call parenthesis. So yet another legitimate way to call the above is
 *
 */
people.each() {
	println "Hello again, ${it}!";
}
// Base on above explanation, this is same as people.findIndexOf(index, Closure)
people.findIndexOf(1) {
	println "Person on second place is ${it}"
}

// Next, method with closure parameter
// First define closure method.
def closureMethod(Closure c) {
	// You can do anything important here.
	c.call();
	// Also you can do another things important here.
}
// Call method with closure parameter.
// This is equivalent with closureMethod({println 'My name is Muhammad Zaky Alvan'});
// with parenthess ignored.
closureMethod {
	println 'My name is Muhammad Zaky Alvan'
}

// Another method with closure arguments and forwarding parameter (to closure) inside.
def otherClosureMethod(String name, Closure c) {
	c.call(name)
}
// This calling otherClosureMethod with two parameter. First is name, and second the closure.
otherClosureMethod("Muhammad Zaky Alvan") {name ->
	println "Hello (again) ${name}!"
}

/**
 * Forwarding parameter construct can be used in circumstances where we have look-up code that needs
 * to be executed before we have access to an object. Say we have customer records
 * that need to be retrieved from a database before we can use them.
 */
// Contract for customer type.
class Customer {
	Integer id
	String name
}
// Fictious call to database.
Customer findCustomerRecord(Integer customerId) {
	new Customer(id : customerId, name : "Customer Sample ${customerId}")
}
// Method with customer id which will be forwarded to printerStrategy
def printCustomerInfo(Integer customerId, Closure printerStrategy) {
	Customer customer = findCustomerRecord(customerId);
	printerStrategy.call customer
	printerStrategy.doCall customer
}
printCustomerInfo(2) {customer ->
	println "Customer id : ${customer.id}, and name : ${customer.name}"
}

// Next, closure with default parameters
def greetString = {greeting, name = 'World' ->
	println "${greeting}, ${name}!"
}
greetString('Hello')
greetString('Hello', 'Zaky')

// Other closure with default parameters. The construct look strange
def dummyWithDefaultParamsClosure = {one = 'Satu', two = 'Dua', three -> 
	println "one is ${one}, two is ${two} and three is ${three}"
}
dummyWithDefaultParamsClosure "Tiga"
dummyWithDefaultParamsClosure("Ica", "Tolu")
dummyWithDefaultParamsClosure("Ica", "'Dua", "Tolu")

// Closure scope.