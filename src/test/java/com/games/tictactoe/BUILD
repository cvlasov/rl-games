package(default_visibility = ["//visibility:public"])

java_test(
  name = "helper",
  size = "small",
  srcs = ["TicTacToeHelperTest.java"],
  test_class = "com.games.tictactoe.TicTacToeHelperTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:junit4",
  ]
)

java_test(
  name = "limitedactionsstate",
  size = "small",
  srcs = ["TicTacToeStateWithLimitedActionsTest.java"],
  test_class = "com.games.tictactoe.TicTacToeStateWithLimitedActionsTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:junit4",
  ],
)

java_test(
  name = "normalgame",
  size = "small",
  srcs = ["TicTacToeNormalGameTest.java"],
  test_class = "com.games.tictactoe.TicTacToeNormalGameTest",
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:junit4",
    "//third_party:mockito",
  ],
)

java_test(
  name = "normalstate",
  size = "small",
  srcs = ["TicTacToeNormalStateTest.java"],
  test_class = "com.games.tictactoe.TicTacToeNormalStateTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:junit4",
  ],
)

java_test(
  name = "symmetricequalitystate",
  size = "small",
  srcs = ["TicTacToeStateWithSymmetricEqualityTest.java"],
  test_class = "com.games.tictactoe.TicTacToeStateWithSymmetricEqualityTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:junit4",
  ],
)
