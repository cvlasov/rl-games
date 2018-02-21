package(default_visibility = ["//visibility:public"])

java_binary(
  name = "tictactoe_epsilon",
  srcs = glob(["*.java"]),
  main_class = "com.games.experiments.TicTacToeEpsilonExperiments",
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:guava",
    "//third_party:opencsv",
  ],
)

java_binary(
  name = "tictactoe_performance",
  srcs = glob(["*.java"]),
  main_class = "com.games.experiments.TicTacToePerformanceExperiments",
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:guava",
    "//third_party:opencsv",
  ],
)
