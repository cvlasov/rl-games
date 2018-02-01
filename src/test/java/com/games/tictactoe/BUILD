package(default_visibility = ["//visibility:public"])

java_test(
  name = "statetest",
  size = "small",
  srcs = ["TicTacToeStateTest.java"],
  test_class = "com.games.tictactoe.TicTacToeStateTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:junit4",
  ],
)

java_test(
  name = "statewithlimitedactionstest",
  size = "small",
  srcs = ["TicTacToeStateWithLimitedActionsTest.java"],
  test_class = "com.games.tictactoe.TicTacToeStateWithLimitedActionsTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:junit4",
  ],
)

java_library(
  name = "tictactoe",
  srcs = glob(["*.java"]),
)
