import json
from collections import OrderedDict
from time import sleep

question_list = []
question_title = []

def make_gammer_question() :

    file_data = OrderedDict()

    title = []
    question = []
    ex = []
    example = []
    answer = []

    f = open('question3.txt', 'r', encoding='utf-8')

    while True :
        line = f.readline()
        if line != '' and line[0] == '-' :
            title.append(line[2:].replace('\n', ''))

        if line != '' and line[0].isdigit() :
            for i in range(len(line)):
                if line[i] == ' ' :
                    question.append(line[i+1:].replace('\n', ''))
                    break
        if line !='' and line[0].isalpha() :

            a = line.find('A)')
            b = line.find('B)')
            c = line.find('C)')
            d = line.find('D)')

            if a != -1 :

                if b != -1 and c != -1 and d != -1 :
                    example.append(line[a:b])
                    example.append(line[b:c])
                    example.append(line[c:d])
                    example.append(line[d:].replace('\n', ''))
                elif b == -1 :
                    example.append(line[a:].replace('\n', ''))
                elif b != -1 and c == -1 :
                    example.append(line[a:b])

            if b != -1 and c == -1 :
                example.append(line[b:].replace('\n', ''))
            if b == -1 and c != -1 :

                if d != -1 :
                    example.append(line[c:d])
                    example.append(line[d:].replace('\n', ''))
                else :
                    example.append(line[c:].replace('\n', ''))
            if c == -1 and d != -1 :
                example.append(line[d:].replace('\n', ''))




            if len(example) == 4 :
                ex.append(example)
                example = []

        if line != '' and line[0] == '*' :
            start = 0
            while True :

                a = line.find('-', start)
                if a != -1 :
                    answer.append(line[a+1])
                    start = a + 1
                else :
                     break


        if not line :
            break

    file_data['title'] = title
    file_data['count'] = len(question)
    data = {}
    for i in range(len(question)) :
        q = 'Question' + str(i)
        data['question'] = question[i]
        data['example'] = ex[i]
        data['answer'] = answer[i]

        file_data[q] = data
        data = {}

    with open('data2.json', 'w') as f :
        json.dump(file_data, f)

    #print(title)
    #print(question)
    #print(ex)
    #print(answer)


def show_question():


    with open('data2.json', 'r') as f:
        json_data = json.load(f)

    question_title.append(json_data['title'])
    cnt = json_data['count']

    for i in range(cnt) :
        qStr = 'Question' + str(i)
        question_list.append(json_data[qStr])

    #print(question_list)


def user_talk() :

    print("Hello We will learn about")
    for i in question_title[0] :
        print(i)
        sleep(1)


    print("Choose between fingerprints A, B, C and D.")
    #(question_list)
    for question in question_list :
        print(question['question'])
        ex = ''
        for i in question['example']:
            ex = ex + i
        print(ex)

        user_answer = input()

        if user_answer == question['answer'] :
            print('perfect!')
            sleep(1)
        else :
            print('The answer is '+ question['answer'])
            sleep(1)


    print("Well done! Good bye~")

#make_gammer_question()
show_question()
user_talk()





