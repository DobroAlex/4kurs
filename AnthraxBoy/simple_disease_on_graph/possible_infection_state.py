import enum


class PossibleInfectionState(enum):
    suspected = 0  # default state
    exposed = 1  # after possible interference with disease transmitter
    incubating = 2  # confirmed sickness, no visual symptoms, no death probability unless of old age, not recovering,
    # already disease source
    sick = 3  # actual sickness, visual symptoms, non-zero death probability, still disease source
    recovered = 4  # if haven't die, person will eventually recover from sickness and become immune
