import enum


class PossiblePersonState(enum):
    not_infected = 0  # save and unexposed, non-suspicious
    dead = 1  # dead but  not buried, may still be source of infection
    buried = 2  # dead and six feet under, can't be source of infection no more
