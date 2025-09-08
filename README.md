Как делать уровни:
В классе LevelHandler, есть метод:
initLandshaftBuilders

Там если тебе надо будет добавляешь уровни, как добавлять:
Делаешь новый класс по типу Level1, только в run надо тогда писать что-то другое:
Для обьектов есть методы:

Декорации: levelHandler.addDecorate
х - горизонталь,
y - вертикаль,
width - ширина,
height - высота,
и айдишник изображения для декорации.

Сквозь декорации можно ходить они нужны просто для красоты,

Аналогично для препятствий:
levelHandler.addObstacle
те же параметры, но сквозь препятствие тигр ходить не может.

аналогично для лаки блоков при ударе головой под которыми или если подойти близко - появляються суши - 1 кусочек:
levelHandler.addEbnutsaGolovojEl
параметры те же, но айди вводить не надо + принцип работы физики как с препятствием,

levelHandler.levelActivity.groundCount - это количество изображений "земли" то по чему ходит тигр,
levelHandler.levelActivity.groundItmWidthInDP - это ширина на одно изображение "земли"
levelHandler.levelActivity.groundItmBackgroundSRC - это айдишник земли то как выглядит одно изображение "земли";

levelHandler.levelActivity.constraintMain.setBackgroundColor(Color.parseColor("цвет")); - устанавливает цвет фона на уровень, есть другой метод ещё levelHandler.levelActivity.constraintMain.setBackGroundResource(дравайб обьект)
он уже устанавливает картинку на задний фон.

дравабл обьект получать надо так:
Drawable drawable = levelHandler.levelActivity.getResources().getDrawable(айдишник);

И последнее что я там делал - змеи:
levelHandler.addSnakeEnemy
параметры:
х - горизонталь,
y - вертикаль,
width - ширина,
height - высота,
айдишник - дефолтное изображение змеи - то как она по умолчанию выглядит,
distanceInBothSides - дистацния в пикселях куда максимум змея ползёт в обе стороны(лево или право),
moverDuration - скорость ползанья змеи

если тигр соприкоснётся с змеёй то игра проиграна и у него высчитываються коини заработанные за этот левел + на след уровень он не переходит.
