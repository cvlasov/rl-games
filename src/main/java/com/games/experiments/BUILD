package(default_visibility = ["//visibility:public"])

java_binary(
  name = "epsilon",
  srcs = [
    "EpsilonExperiments.java",
    "ExperimentHelper.java",
  ],
  main_class = "com.games.experiments.EpsilonExperiments",
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/chungtoi:chungtoi",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:opencsv",
  ],
)

java_binary(
  name = "performance",
  srcs = [
    "PerformanceExperiments.java",
    "ExperimentHelper.java",
  ],
  main_class = "com.games.experiments.PerformanceExperiments",
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/chungtoi:chungtoi",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:opencsv",
  ],
)
