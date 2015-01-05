// Dot spread and spread operator example
def people = ['one' : 'Muhammad Zaky Alvan', 'two' : 'Ahmad Radhy']
def keys = ['one', 'two']
def values = ['Muhammad Zaky Alvan', 'Ahmad Radhy']

assert people*.key == keys
assert people*.key[0] == keys[0]
assert people*.value == values
assert people*.value[1] == 'Ahmad Radhy'

// Next another spread dot operator, calling method.
class Greeter {
	String target
	String greeting = "Hello";
	
	String greet() {
		"${greeting}, ${target}"
	}
}

def greeters = [
	new Greeter(target : 'Zaky'), new Greeter(target : 'Radhy')
]
greeters*.greet().each({greeting -> println greeting})

// Operator overloading, what the fuck is this?
def today = new Date()
def tomorrow = today + 1
def yesterday = today - 1

assert today.plus(1) == tomorrow
assert today.minus(1) == yesterday