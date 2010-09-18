function neighbors(board, row, column){
	
	return [
		board[row-1][column-1],
		board[row-1][column],
		board[row-1][column+ 1],

		board[row][column-1],
		board[row][column+1],

		board[row+1][column-1],
		board[row+1][column],
		board[row+1][column+ 1],
	];
}

function countLive(board, row, column){
	var aliveCount=0;

	aliveCount += board[row-1][column-1];
	aliveCount += board[row-1][column];
	aliveCount += board[row-1][column+ 1];

	aliveCount += board[row][column-1];
	aliveCount += board[row][column+1];

	aliveCount += board[row+1][column-1];
	aliveCount += board[row+1][column];
	aliveCount += board[row+1][column+ 1];
	return aliveCount;
}


function shouldDie(board, row, column){
	return ((countLive(board, row, column) < 2) || (countLive(board, row, column) > 3)) && board[row][column];
}

function shouldResurrect(board, row, column){
	return (countLive(board, row, column) == 3) && !board[row][column];
}

function drawBoard(board){
	for(var row=0; row< board.length; row++)
	{
		for(var column=0; row< board[row].length; row++){
	$("#gameBoard").append(>")
}
	{
	
}
}

	$("#gameBoard").append(>")
