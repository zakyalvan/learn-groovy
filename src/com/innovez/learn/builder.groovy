// Groovy's builder like example.
def method1(params, Closure closure) {
	println "method1 called with params ${params}"
	closure.call();
}
def method2(Closure closure) {
	println "method2 called"
	closure.call();
}
def method3(parameter) {
	println "method3 called with parameter ${parameter}"
}

method1(param : 'One') {
	method2() {
		method3('Hohoh')
	}
	method1(123) {
		method1('nested') {
			method3 10
		}
	}
}
