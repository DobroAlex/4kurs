import enum


class PossiblePersonState(enum):
    not_infected = 0  # save and unexposed, non-suspicious, synonymous for alive. Person may switch to that state
    # after recovering from all infections
    infected = 1    # infected with any infection. Should be set if person infected with any infection
    dead = 2  # dead but  not buried, may still be source of infection
    buried = 3  # dead and six feet under, can't be source of infection no more
