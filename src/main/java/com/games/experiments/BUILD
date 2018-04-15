package(default_visibility = ["//visibility:public"])

java_binary(
  name = "epsilon_chung_toi",
  srcs = ["ChungToiEpsilonExperiment.java"],
  main_class = "com.games.experiments.ChungToiEpsilonExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "epsilon_nim",
  srcs = ["NimEpsilonExperiment.java"],
  main_class = "com.games.experiments.NimEpsilonExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "epsilon_tictactoe_limited_actions",
  srcs = ["TicTacToeLimitedActionsEpsilonExperiment.java"],
  main_class = "com.games.experiments.TicTacToeLimitedActionsEpsilonExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "epsilon_tictactoe_normal",
  srcs = ["TicTacToeNormalEpsilonExperiment.java"],
  main_class = "com.games.experiments.TicTacToeNormalEpsilonExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "epsilon_tictactoe_symmetric_equality",
  srcs = ["TicTacToeSymmetricEqualityEpsilonExperiment.java"],
  main_class = "com.games.experiments.TicTacToeSymmetricEqualityEpsilonExperiment",
  deps = [":experiment_deps"],
)

java_library(
  name = "experiment_deps",
  srcs = ["ExperimentHelper.java"],
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/chungtoi:chungtoi",
    "//src/main/java/com/games/nim:nim",
    "//src/main/java/com/games/tictactoe:tictactoe",
    "//third_party:opencsv",
  ],
)

java_binary(
  name = "performance_chung_toi",
  srcs = ["ChungToiPerformanceExperiment.java"],
  main_class = "com.games.experiments.ChungToiPerformanceExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "performance_nim",
  srcs = ["NimPerformanceExperiment.java"],
  main_class = "com.games.experiments.NimPerformanceExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "performance_nim_ES",
  srcs = ["NimExploringStartsPerformanceExperiment.java"],
  main_class = "com.games.experiments.NimExploringStartsPerformanceExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "performance_tictactoe_limited_actions",
  srcs = ["TicTacToeLimitedActionsPerformanceExperiment.java"],
  main_class = "com.games.experiments.TicTacToeLimitedActionsPerformanceExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "performance_tictactoe_normal",
  srcs = ["TicTacToeNormalPerformanceExperiment.java"],
  main_class = "com.games.experiments.TicTacToeNormalPerformanceExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "performance_tictactoe_symmetric_equality",
  srcs = ["TicTacToeSymmetricEqualityPerformanceExperiment.java"],
  main_class = "com.games.experiments.TicTacToeSymmetricEqualityPerformanceExperiment",
  deps = [":experiment_deps"],
)

java_binary(
  name = "tictactoe_policy_comparison",
  srcs = ["TicTacToePolicySizeComparison.java"],
  main_class = "com.games.experiments.TicTacToePolicySizeComparison",
  deps = [
    "//src/main/java/com/games/agents:agents",
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/tictactoe:tictactoe",
  ],
)
