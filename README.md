# WordleBot
A bot that plays wordle for you.

## Algorithm

It uses a dictionary of words provided by the user. It makes use of the hints given by wordle(Correct,Present,Wrong) to narrow the possibilities
down to a smaller set of words.

## Ideas that can make the algorithm more accurate(IMPLEMENTED)

The current algorithm utilizes three hints:

1. Character i is present and in the correct position.
2. Character i is present, but not in the correct position.
3. Character i is not present at all.

Observant people will notice there's one more hint we can use. It is an extension of hint 2. It is:

Remove all words from the possible_words list that have Character i in the incorrect position. Enforcing this rule will result in an even 
smaller set of possibilities. In order to implement it, we're going to need a HashMap. That's the simplest way. The two fields in the hashmap will be a character,and an integer(the position). We will make use of this HashMap in the P option. Whenever P is entered, an entry will be added to the HashMap, with the position as key, and the character as value. And then our problem is converted to a simple check.

I implemented it. I actually managed to find some bugs in WordleBot too. The bug i discovered was:

The checks were happening correctly. There was nothing wrong with the logic of the checks itself. The mistake was putting the exit statement at the end of the checks. Since I didn't use anything like `else if`, the checks kept happening, one by one, and they kept removing the words. The words were being removed without even being looked at, because I used the variable candidate to store the candidate string.

How did I notice this? I wanted to compare the two versions, so I added a statement that prints the size of possible_words, and when I saw that `ImprovedWordleBot` reduced the number of possibilities to `6591`, and `WordleBot` reduced it to `6419`. I started looking for bugs in `ImprovedWordleBot` because I was sure that `WordleBot` was correct. Then I switched over to `WordleBot` and noticed that there was nothing preventing the other checks from executing. 

## Why am I writing so much about a wordle bot?

Because this is the first time I've built something that is actually helpful to me, as a human. That's why I started coding, well no, I started because I wanted to hack into NASA, but what kept me going is the idea that I can make something that actually benefits me. Sure, it's a Wordle bot, as simple as a tic-tac-toe bot, but the complexity of the project does not matter to me. It's usefulness does. Today a wordle bot, tomorrow a home automation system.
