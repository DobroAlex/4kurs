from person import person


class Agent(person):
    def __init__(self, age: int, sex: chr, receptivity: float, infected_with: dict = dict(),
                 visited_nodes: list = list()):
        super().__init__(age, sex, receptivity, infected_with)
        self.visited_nodes = visited_nodes
