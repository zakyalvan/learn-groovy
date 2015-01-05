// They tell following are traditional way
def sum(n) {
	def result = 0;
	for(i = 2; i <= n; i+=2) {
		result += i
	}
	result
}
println "Sum of even numbers from 1 to 10 is ${sum 10}"

def product(n) {
	def prod = 1
	for(i = 2; i <= n; i += 2) {
		prod *= i
	}
	prod
}
println "Product of even numbers from 1 to 10 is ${product 10}"

def square(n) {
	def squared = []
	for(i = 2; i <= n; i+=2) {
		squared.add i*i;
	}
	squared
}
println "Squared of numbers from 1 to 10 are ${square 10}"

// And following are Groovy way, using closure.
def pickEven(n, Closure block) {
	for(i = 2; i <= n; i += 2) {
		block(i)
	}
}
def total = 0
pickEven(10) {
	total += it
}
println "Sum of even numbers from 1 to 10 is ${total}"
def prod = 1
pickEven(10) {
	prod *= it
}
println "Product of even numbers from 1 to 10 is ${prod}"
def squared = []
pickEven(10) {
	squared.add it*it
}
println "Squared of even numbers from 1 to 10 are ${squared}"

// Caching (assign to variable) of closure sample
class Equipment {
	def calculator;
	Equipment(Closure clcltr) {
		calculator = clcltr;
	}
	def simulate() {
		println 'Start simulation'
		calculator()
	}
}
def eq1 = new Equipment({println 'Calculator 1'});
def calculator = {println 'Calculator 2'};
def eq2 = new Equipment(calculator)
def eq3 = new Equipment(calculator);

eq1.simulate()
eq2.simulate()
eq3.simulate()

// Another usage of closure
class Resource {
	def open() {print 'Opened...'}
	def read() {print 'Read...'}
	def write() {throw new RuntimeException()}
	def close() {print 'Closed...'}
	
	def static use(Closure closure) {
		Resource resource = new Resource();
		try {
			resource.open();
			closure resource
		}
		finally {
			resource.close();
		}
	}
}
Resource.use {Resource resource ->
	resource.read()
	//resource.write()
}

// Next, curry feature on closure
def processDailyMessages(Closure processor) {
	Date now = new Date()
	now.upto(now + 10) {Date date ->
		println '====================================================='
		def dailyProcessor = processor.curry(date);
		['First message of the day', 'Second message of the day', 'Third message of the day'].each {message ->
			dailyProcessor message
		}
	}
}
processDailyMessages {Date date, String message ->
	println "${date} : ${message}";
}