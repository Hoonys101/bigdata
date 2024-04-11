import turtle
import time
import random
import math

delay = 0.1
score = 0
high_score = 0

# 화면 설정
wn = turtle.Screen()
wn.title("Snake Game")
wn.bgcolor("black")
wn.setup(width=600, height=600)
wn.tracer(0)  # 화면 갱신 중단

# 도망자
head = turtle.Turtle()
head.speed(0)
head.shape("turtle")
head.color("white")
head.penup()
head.goto(0, 0)
head.direction = "stop"

# 먹이
food = turtle.Turtle()
food.speed(0)
food.shape("circle")
food.color("red")
food.penup()
food.goto(0, 100)

# 술래 turtle
catcher = turtle.Turtle()
catcher.speed(0)
catcher.shape("turtle")
catcher.color("red")
catcher.penup()
catcher.goto(250, 250)
catcher.direction = "stop"

# 점수 표시
pen = turtle.Turtle()
pen.speed(0)
pen.shape("square")
pen.color("white")
pen.penup()
pen.hideturtle()
pen.goto(0, 260)
pen.write("Score: 0  High Score: 0", align="center",
          font=("Courier", 24, "normal"))


# 함수
def go_up():
    if head.direction != "down":
        head.direction = "up"


def go_down():
    if head.direction != "up":
        head.direction = "down"


def go_left():
    if head.direction != "right":
        head.direction = "left"


def go_right():
    if head.direction != "left":
        head.direction = "right"


def move():
    if head.direction == "up":
        y = head.ycor()
        head.setheading(90)
        head.sety(y + 10+int(score/100)*10)

    if head.direction == "down":
        y = head.ycor()
        head.setheading(-90)
        head.sety(y - 10-int(score/100)*10)

    if head.direction == "left":
        head.setheading(180)
        x = head.xcor()
        head.setx(x - 10-int(score/100)*10)

    if head.direction == "right":
        head.setheading(0)
        x = head.xcor()
        head.setx(x + 10+int(score/100)*10)


# 키보드 바인딩
wn.listen()
wn.onkeypress(go_up, "w")
wn.onkeypress(go_down, "s")
wn.onkeypress(go_left, "a")
wn.onkeypress(go_right, "d")

# 메인 게임 루프
while True:
    wn.update()
    
    # 술래가 머리 따라가기
    dx = head.xcor() - catcher.xcor()
    dy = head.ycor() - catcher.ycor()
    dist = math.sqrt(dx ** 2 + dy ** 2)
    if dist > 20:
        catcher.setheading(math.degrees(math.atan2(dy, dx)))
        catcher.forward(score/15)

    # 벽 충돌 확인
    if (head.xcor() > 290 or head.xcor() < -290 or head.ycor() > 290 or head.ycor() < -290) or dist < 20:
        time.sleep(1)
        head.goto(0, 0)
        head.direction = "stop"
        score = 0
        pen.clear()
        pen.write("Score: {}  High Score: {}".format(score, high_score),
                  align="center", font=("Courier", 24, "normal"))

    # 먹이 먹기
    if head.distance(food) < 20:
        x = random.randint(-290, 290)
        y = random.randint(-290, 290)
        food.goto(x, y)

        # 점수 증가
        score += 10
        if score > high_score:
            high_score = score
        pen.clear()
        pen.write("Score: {}  High Score: {}".format(score, high_score),
                  align="center", font=("Courier", 24, "normal"))

    move()

    time.sleep(delay)

wn.mainloop()