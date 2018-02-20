package(default_visibility = ["//visibility:public"])

java_binary(
  name = "tictactoe",
  srcs = glob(["*.java"]),
  main_class = "com.games.experiments.TicTacToeExperiments",
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:guava",
    "//third_party:opencsv",
  ],
)
