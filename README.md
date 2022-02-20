# CommunalPayment
Коммунальные платежи
Сущности
1. Пользователь
a. ФИО
b. Е-мейл (выступает в качестве логина)
c. телефон
2. Платежный адрес пользователя
a. Уникальный идентификатор
b. Строка с адресом
c. Связь с пользователем
3. Шаблон
a. Уникальный идентификатор
b. Наименование шаблона
c. Iban
d. Назначение платежа
e. Связь с адресом пользователя
У одного пользователя может быть несколько платежных адресов. Под адресом может
быть несколько шаблонов.
4. Оплата
a. Уникальный идентификатор
b. Шаблон
c. Номер карты
d. Сумма
e. Статус(Новый, Оплачен, Провален)
f. Дата+время создания
g. Дата+время изменения статуса
Все сущности должны иметь отражение в базе данных
Процесс оплаты
1) Создаем оплату в статусе новый
2) В отдельном потоке 1 раз в секунду вычитываем оплаты в статусе новый
3) Запрашиваем статус у компонента, который первые 2 секунды для платежа
всегда выдает статус новый, а после 2-й секунды с одинаковой вероятностью
выдает все 3 статуса
Сценарий выполнения демонстрационной программы:
1) Зарегистрировать пользователя
2) Завести ему 2 адреса
3) Под каждым адресом создать по 1-2 шаблона
4) Создать несколько платежей
5) Дождаться, когда платежи перейдут в финальный статус
