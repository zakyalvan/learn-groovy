def builder = new groovy.xml.MarkupBuilder();

// Less line
def markup = builder.book{
	author 'Muhammad Zaky Alvan'
	publishedYear '2014'
}

// More line
def closure = {
	author 'Ahmad Radhy'
	publisher 'Andi Yogyakarta'
};
def other = builder.book(closure);

// Equivalent.
other = builder.book closure;

other = builder.book {
	aaa 'iii'
	bbb 'ooo'
}