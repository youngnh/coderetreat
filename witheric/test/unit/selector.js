module("gameoflife");

test("neighbor1", function() {
	var board = [[0, 0, 0],
		     [0, 0, 0],
		     [0, 0, 0]];
       	same([0, 0, 0, 0, 0, 0, 0, 0], neighbors(board, 1, 1));
});

test("neighbor2", function() {
	var board = [[1, 0, 1],
		 [1, 0, 0],
		 [1, 1, 1]];
       	same([1, 0, 1, 1, 0, 1, 1, 1], neighbors(board, 1, 1));
});

test("countLive", function() {
	var board = [[0, 0, 0],
		     [0, 0, 0],
		     [0, 0, 0]];
	equals(0,countLive(board, 1, 1));

	board = [[1, 0, 0],
		 [0, 0, 0],
		 [0, 0, 0]];
	equals(1,countLive(board, 1, 1));


board = [[1, 0, 0],
		     [1, 0, 0],
		     [0, 0, 0]];
	equals(2,countLive(board, 1, 1));


board = [[0, 0, 0],
		     [0, 0, 0],
		     [1, 1, 1]];
	equals(3,countLive(board, 1, 1));


});

test("shouldDieRule1", function() {
    var board = [[0, 0, 0],
		     [0, 1, 0],
		     [0, 0, 0]];
    ok(shouldDie(board, 1, 1));

board = [[0, 0, 0],
		     [0, 0, 0],
		     [0, 0, 0]];
    ok(!shouldDie(board, 1, 1), "because he's dead");

board = [[0, 0, 0],
		     [1, 1, 0],
		     [0, 0, 0]];
	ok(shouldDie(board, 1, 1));
});

test("shouldDieRule2", function() {
    var board = [[1, 1, 1],
		     [1, 1, 0],
		     [0, 0, 0]];
	ok(shouldDie(board, 1, 1));
});

test("shouldDieRule3", function() {
    var board = [[1, 1, 0],
		     [1, 1, 0],
		     [0, 0, 0]];
	ok(!shouldDie(board, 1, 1));

	board = [[1, 1, 0],
		     [0, 1, 0],
		     [0, 0, 0]];
	ok(!shouldDie(board, 1, 1));
});

test("shouldResurrect", function() {
    var board = [[1, 1, 0],
		     [1, 0, 0],
		     [0, 0, 0]];
	ok(shouldResurrect(board, 1, 1));

board = [[1, 1, 0],
		     [1, 1, 0],
		     [0, 0, 0]];
	ok(!shouldResurrect(board, 1, 1));
});

test("drawBoard", function() {
var board = [[0, 0, 0],
		     [0, 0, 0],
		     [0, 0, 0]];
	drawBoard(board);
	equals(9, $(".cell").length);	
});
