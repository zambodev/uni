#include <iostream>

class date {

private:
    int day;
    int month;
    int year;

    bool validate() {
        //Code...
    }

    void nextDay() {
        //Code...
    }

public:
    bool read() {
        std::cin >> day >> month >> year;
        if (validate()) {
            return true;
        }
        else {
            day = month = year = 0;
            return false;
        }
    }

    void print() {
        std::cout << day << '/' << month << '/' << year << std::endl;
    }

    bool equals(date other) {
        return year == other.year && month == other.month && day == other.day;
    }
};

int main() {

    date dat;
    dat.read();
    dat.print();

    date dat2;
    dat2.read();
    dat2.print();

    
    return 0;
}