import groovy.xml.MarkupBuilder

// Named parameters, all map parameters element will be collected and passed as method first argument
def withMapParameters(parameters, name) {
	assert parameters.one == 'Satu'
	assert parameters.two == 'Dua'
	assert parameters.three == 'Tiga'
	assert name == 'Muhammad Zaky Alvan'
}
withMapParameters('one' : 'Satu', 'Muhammad Zaky Alvan', 'two' : 'Dua', 'three' : 'Tiga');
withMapParameters('Muhammad Zaky Alvan', 'one' : 'Satu', 'two' : 'Dua', 'three' : 'Tiga');

// Markup builder example
def builder = new MarkupBuilder();
def customers = builder.customers {
	customer(id : 1001) {
		name(fullName : 'Muhammad Zaky Alvan')
		address(street : '1 Jalan Jalak', city : 'Bandung')
	}
	customer(id : 1002) {
		name(fullName : 'Ahmad Radhy')
		address(street : 'Pogung Dalangan', city : 'Yogyakarta')
	}
}
// Instance method pointer
def characterList = ['A', 'B', 'C', 'D']

def addit = characterList.&add
addit 'E'
assert characterList == ['A', 'B', 'C', 'D', 'E']

/**
 The difficulty with method pointers to instance methods is being sure what instance
 the method pointer is referencing. In essence, an instance method pointer violates
 the encapsulation rules for the object by passing control to an object that is outside
 the direct control of a class. So I recommend caution when using them. However,
 method pointers when applied to static methods can be a very useful way to create
 DSL shortcut keywords.
 */

// Let try using static method reference
class StringUtils {
	def static toUpperCase(String input) {
		input.toUpperCase();
	}
}
def upperCase = StringUtils.&toUpperCase
assert upperCase('muhammad zaky alvan') == 'MUHAMMAD ZAKY ALVAN'

// Lets move groovy's meta object protocol (meta-object programming)
// Mop feature itself consists of four distinct things
// 1. Reflection 2. Metaclass 3. Categories and 4. Expandos

// Let start with expando
def customer = new Expando();
assert customer.properties == [:]
assert customer.id == null

customer.id = 1;
customer.name = 'Muhammad Zaky Alvan'

assert customer.properties == [id : 1, name : 'Muhammad Zaky Alvan']

// Add 'method' like capability to expando
customer.prettyPrint = {
	println 'Customer has following properties :'
	customer.properties.each {property ->
		if(property.key != 'prettyPrint') {
			println "${property.key} = ${property.value}"
		}
	}
}
customer.prettyPrint()

// Next category, how to add method on any existing classes using 'use' method
class Person {
	int id;
	String name;
}

def zaky = new Person(id : 1, name : 'Muhammad Zaky Alvan')
def radhy = new Person(id : 2, name : 'Ahmad Radhy')

def people = [zaky, radhy]

class PersonPrinter {
	// Static method and with target type parameter, parameter named 'self' by convention
	static prettyPrint(Person self) {
		println 'Person has following properties :'
		for(property in self.properties) {
			if(property.key != 'class') 
				println "${property.key} = ${property.value}"
		}
	}
	static id(Person self) {
		println "Id of person with name ${self.name} is ${self.id}"
	} 
}

use(PersonPrinter) {
	for(person in people) {
		person.prettyPrint()
		person.id();
		
		def printId = person.&id
		
		// We can use method pointer even on dynamically added method.
		printId()
	}
}

/**Next we try feature of metaclass. We can intercept method call using invokeMethod on called object. 
 * Please remember, in groovy method called indirectly via invokeMethod (method from GroovyObject, implemented implicitly by groovy compiler).
 * By default that call to invoke method relayed to metaclass of called object.
 */
class Student {
	Integer id
	String name
	
	Object invokeMethod(String methodName, Object args) {
		if(methodName.equals('prettyPrint')) {
			println "Student details -> Id : ${this.id}, Name : ${this.name}"
		}
	}
}
def student = new Student(id : 123, name : 'Muhammad Zaky Alvan')
student.prettyPrint()

// Inject custom property into metaclass.
student.metaClass.injectedProperty = 100
assert student.injectedProperty == 100

// We all so can inject any custom method into metaclass.
student.metaClass.injectedMethod() {
	'From injected method!'
}
assert student.injectedMethod() == 'From injected method!';

// Next, learn about closure scope
class ContainingClosure {
	def closureSample = {
		println this.class.name
		println owner.class.name
		println delegate.class.name
	}
	
	def methodCallingClosure() {
		println 'Calling closure on CantainingClosure instance method'
		closureSample();
	}
	
	def delegatableMethod() {
		println 'Huhuhu, from delegable method on instance containing closure'
	}
}

def withClosureCaller = new ContainingClosure();
withClosureCaller.methodCallingClosure();

withClosureCaller.closureSample.delegatableMethod();

println 'Calling closure on script'
def thisIsClosure = withClosureCaller.closureSample;
thisIsClosure();

// Using closure defined on script
def otherClosure = {
	println this.class.name
	println owner.class.name
	println delegate.class.name
}

println 'Calling closure defined outside class'
otherClosure()

class Delegatable {
	def delegatableMethod() {
		println 'From delegatable method'
	}
}

def delegatable = new Delegatable()
otherClosure.delegate = delegatable
otherClosure.delegatableMethod()


// Expando metaclass
String.metaClass.search = {->
	println delegate;
}
"query string".search();

String.metaClass.otherSearch() {
	def doSearch = {
		println this
	};
	doSearch();
}
"huhuhu query string sample".otherSearch()