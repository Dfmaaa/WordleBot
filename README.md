# WordleBot
A bot that plays wordle for you.

## Algorithm

It uses a dictionary of words provided by the user. It makes use of the hints given by wordle(Correct,Present,Wrong) to narrow the possibilities
down to a smaller set of words.

## Ideas that can make the algorithm more accurate

The current algorithm utilizes three hints:

1. Character i is present and in the correct position.
2. Character i is present, but not in the correct position.
3. Character i is not present at all.

Observant people will notice there's one more hint we can use. It is an extension of hint 2. It is:

Remove all words from the possible_words list that have Character i in the incorrect position. Enforcing this rule will result in an even 
smaller set of possibilities. I'll probably implement it.
