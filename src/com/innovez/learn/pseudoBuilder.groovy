// Another example of groovy's pseudo builder
class CurrentContainer {
	def static current;
}

def root(Closure closure) {
	def tree = [:]
	def root = [:]
	
	tree['root'] = root
	
	def parent = CurrentContainer.current;
	CurrentContainer.current = root
	closure.call()
	CurrentContainer.current = parent
	return tree
}

def node(key, Closure closure) {
	def node = [:]
	
	CurrentContainer.current[key] = node
	
	def parent = CurrentContainer.current
	CurrentContainer.current = node
	closure.call()
	CurrentContainer.current = parent
}

def leaf(key, value) {
	CurrentContainer.current[key] = value
}

def tree = root() {
	node('sub-tree-1') {
		leaf('leaf-1', 'leaf object 1')
		leaf('leaf-2', 'leaf object 2')
	}
	node('sub-tree-2') {
		leaf('leaf-3', 'leaf object 3')
	}
}
println tree