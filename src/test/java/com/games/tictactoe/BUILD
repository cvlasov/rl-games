package(default_visibility = ["//visibility:public"])

java_test(
  name = "tictactoestatetest",
  srcs = ["TicTacToeStateTest.java"],
  test_class = "com.games.tictactoe.TicTacToeStateTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:junit5",
  ],
)

filegroup(
  name = "all-tests",
  srcs = glob(["*.java"]) + ["BUILD"],
)