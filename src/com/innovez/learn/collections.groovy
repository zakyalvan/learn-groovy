// List
def listThings = ["Muhammad", "Zaky", "Alvan"];
println listThings;
println listThings[1];
println listThings.class.name;

// Set
Set setThings = ["Muhammad", "Zaky", "Alvan"];
println setThings;
println setThings.getClass().getName();

//or
def anotherSetThings = ["Muhammad", "Zaky", "Alvan"] as Set;
println anotherSetThings;
println anotherSetThings.getClass().getName();

// Map (Linked hash map)
def mapThings = ["red" : 1, "green" : 2, "blue" : 3];
println mapThings;

// Or, if string key without space
def otherMapThings = [red : 2, green : 2, blue : 3];
println otherMapThings;
println otherMapThings.red;
println otherMapThings.getClass().getName();