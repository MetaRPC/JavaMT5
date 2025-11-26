# Решение проблем / Troubleshooting

## Проблема: "Unresolved compilation problem" или примеры работают "через раз"

### Симптомы:
```
java.lang.Error: Unresolved compilation problem:
     at mt5_term_api.ChartsGrpc.newBlockingStub(ChartsGrpc.java:112)
```

Или примеры иногда запускаются, иногда падают с ошибкой компиляции.

### Причина:
Maven daemon кеширует в памяти сгенерированные protobuf файлы. Иногда protoc генерирует файлы с ошибками (особенно ChartsGrpc.java), и daemon продолжает использовать сломанную версию из кеша.

### Решение 1: Используйте run-clean.bat

Вместо обычного `run.bat` используйте `run-clean.bat` для гарантированно чистой сборки:

```batch
# Обычный запуск (может ломаться)
.\run.bat 9

# Надежный запуск (всегда работает)
.\run-clean.bat 9
```

`run-clean.bat` делает:
1. Останавливает Maven daemon
2. Удаляет папку `target/`
3. Компилирует проект заново
4. Запускает пример

### Решение 2: Ручная очистка

Если проблема повторяется часто:

```batch
# 1. Остановить daemon
mvnd --stop

# 2. Удалить target
rmdir /s /q target

# 3. Собрать заново
mvnd compile

# 4. Запустить пример
.\run.bat 9
```

### Решение 3: Перезапустить daemon

Иногда достаточно просто перезапустить daemon:

```batch
mvnd --stop
.\run.bat 9
```

## Проблема: ClassNotFoundException для gRPC классов

### Симптомы:
```
java.lang.ClassNotFoundException: io.grpc.internal.NoopClientStream
```

Обычно происходит в shutdown hooks.

### Причина:
Exec plugin использует изолированный classloader, который не включает все внутренние классы gRPC.

### Решение:
Это не критично - ошибка происходит только при аварийном завершении через Ctrl+C. Программа уже отработала корректно. Просто игнорируйте эти WARNING сообщения.

## Проблема: Maven daemon DaemonException

### Симптомы:
```
DaemonException$StaleAddressException: Could not receive a message from the daemon.
```

### Причина:
Daemon "упал" или завис из-за проблем с компиляцией.

### Решение:
```batch
mvnd --stop
.\run-clean.bat <номер-примера>
```

## Когда использовать какой скрипт

| Скрипт | Когда использовать |
|--------|-------------------|
| `run.bat` | Обычный запуск (быстро, но может сломаться) |
| `run-clean.bat` | Если `run.bat` падает с ошибкой компиляции |
| `mvnd clean compile` | Ручная пересборка без запуска примера |

## Профилактика

Если примеры постоянно ломаются, рекомендуется:

1. **Перезапускать daemon регулярно:**
   ```batch
   mvnd --stop
   ```

2. **Использовать `run-clean.bat` для первого запуска дня**

3. **Следить за версией protoc:**
   Убедитесь что protoc совместим с вашей версией protobuf-maven-plugin (0.6.1)

## Дополнительная диагностика

Посмотреть логи daemon:
```batch
# Windows
type %USERPROFILE%\.m2\mvnd\registry\1.0.3\daemon-*.log | more

# Или найти файл вручную:
dir /s %USERPROFILE%\.m2\mvnd\registry\*.log
```

Проверить статус daemon:
```batch
mvnd --status
```

Остановить все daemon'ы:
```batch
mvnd --stop
```
