/**
 * We will use following sample dsl
 * 
 * move forward and then turn left
 * jump fast, forward and then turn right
 */
def (forward, left, then, fast, right) = ['forward', 'left', '', 'fast', 'right']
def move(dir) {
	println "Moving ${dir}"
	this
}
def and(blah) {
	this
}
def turn(dir) {
	println "Turning ${dir}"
	this
}
def jump(speed, dir) {
	println "Jumping ${speed} and ${dir}"
	this
}

println '- First sample : move forward and then turn left'
move forward and then turn left

println '- Second sample : jump fast, forward and then turn right'
jump fast, forward and then turn right


/**
 * Another simple example using closure
 */
def show = {println it}
def square_root = {Math.sqrt(it)}
def please(action) {
	[the : {what ->
		[of : {n -> 
			action(what(n))
		}]
	}]
}
please show the square_root of 200