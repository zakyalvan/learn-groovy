def dummyMethod(Closure closure) {
	println "Dummy method of ${this.class.name} called"
	closure.delegate = this
	closure()
}
def otherDummyMethod(String name, Closure closure) {
	println "Other dummy method of ${this.class.name} called"
	closure.call(name)
}

dummyMethod {
	println 'First closure called'
	otherDummyMethod('Muhammad Zaky Alvan') {String name ->
		println "Second closure called, ${name}"
	}
}

class DummyType {
	def dummyMethod(Closure closure) {
		closure.delegate = this
		closure.setResolveStrategy(Closure.DELEGATE_FIRST)
		println "Dummy method of ${this.class.name} called"
		closure.call()
	}
	def otherDummyMethod(String name, Closure closure) {
		println "Other dummy method of ${this.class.name} called"
		closure.call(name)
	}
}

def dummy = new DummyType()
dummy.dummyMethod {
	otherDummyMethod('zakyalvan') {String name ->
		println "Hihihihi...${name}"
	}
}