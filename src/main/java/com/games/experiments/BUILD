package(default_visibility = ["//visibility:public"])

java_binary(
  name = "chungtoi_epsilon",
  srcs = glob(["ChungToiEpsilonExperiments.java"]),
  main_class = "com.games.experiments.ChungToiEpsilonExperiments",
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/chungtoi:chungtoi",
    "//third_party:guava",
    "//third_party:opencsv",
  ],
)

java_binary(
  name = "tictactoe_epsilon",
  srcs = glob(["TicTacToeEpsilonExperiments.java"]),
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
  srcs = glob(["TicTacToePerformanceExperiments.java"]),
  main_class = "com.games.experiments.TicTacToePerformanceExperiments",
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:guava",
    "//third_party:opencsv",
  ],
)
