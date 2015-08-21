namespace java ru.atc.uss.hello

enum Title {
  MR = 1,
  MS = 2,
  MISS = 3,
  MRS = 4
}

service Hello {

    string sayHello(1:string name, 2:Title title)

}