## Lanterna

Simple encapsulation of the Google Lanternal text terminal, just a quick way of adding image functionality to
anything.

https://code.google.com/archive/p/lanterna/
https://github.com/mabe02/lanterna

```
final Lanterna lanterna = new Lanterna(80, 24, "Test");
lanterna.drawString("Hello world ...", 10, 12, Color.GREEN, Color.BLUE);
lanterna.refreshAndSleep(1000);
lanterna.close();
```

Simon Garton  
simon.garton@gmail.com  
simongarton.com  
January 2024