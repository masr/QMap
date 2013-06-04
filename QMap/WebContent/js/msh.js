function arrayRemove(array, index) {
	for (i = index; i < array.length - 1; i++)
		array[i] = array[i + 1];
	array.pop();
}


function ABC(i) {
	return String.fromCharCode(i % 26 + 65);
}
