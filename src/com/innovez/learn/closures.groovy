maximize = {firstNumber, secondNumber -> 
	firstNumber > secondNumber ? firstNumber : secondNumber
}

// Using call example.
def result = maximize.call(4,5)
println "Max of (4,5) : ${result}"

// Invoke directly
result = maximize(32.43, 32.42)
println "Max of (32.43, 32.42) : ${result}"

// Multi-statements closures example.
def maxInList = {list ->
	def tempMax = list[0];
	for(i in list) {
		if(i > tempMax) 
			tempMax = i;
	}
	tempMax;
}
def numberList = [32,2,90,43,4,17];
println maxInList(numberList);

// Simple closure example.
def greeting = 'World'
def greet = {"Hello, ${greeting}"}
println greet ()

// Simple closure with parameter
def greetClosure = {name -> "Hello, ${name}"}
println greetClosure ('Muhammad Zaky Alvan');

// Using special "it" parameter name on closure as method parameter.
def fruits = ['Apple', 'Banana', 'Melon'] as Set;
fruits.each {item -> println "I also like ${item}"};
fruits.each	({println "I like ${it}s"})
fruits.each {println "Hmm, I like ${it}s"}
