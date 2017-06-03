# ParsingLibrary
After reading [Building Parsers With Java](http://xp123.com/oozinoz/bpwj.htm) by Steven John Metsker, I used the library from the book's site for a few pet projects.  The book and library make writing simple parsers fairly easy, and the book and code are a good resource in learning how parsers work.  

Working with the code however, I found that sometimes I would end up with ClassCastExceptions as well as some other problems that generics are good for.  With that in mind I edited the code base to use generics, now each Parser and Assembly takes three generic parameters Tok, Val, and Tar.  

Tok is a parameter for token type; this parameter is usually provided by the implementing class of Parser. When Tok is not provided it will be either Token or Character, and must be the same as any sub-parsers.  

Val is a parameter for another type that will be on the Assembly's stack; this will be either one type, like Integer, or an amalgamation type, like TypeOrType, or an abstract base class that is parent to all types used by the parser. This will usually be something the assembler creates and pushes back on the stack.

Tar is a parameter for the PubliclyClonable Target of the parser.  It must be PubliclyCloneable<Tar>

These changes make code using this version of the parsing toolkit a bit more verbose, but much more explicit and  less prone to errors. 

I have also eliminated all warnings in the code.

In my porting I have unfortunately lost some of the commenting that was in the code.  I will fix this as time goes on. 



