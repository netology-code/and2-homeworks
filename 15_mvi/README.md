# Домашнее задание к занятию «4.4. Архитектуры: MVC, MVI»

В качестве результата пришлите ссылку на ваш Pull Request в личном кабинете студента на сайте [netology.ru](https://netology.ru).

**Важно**: ознакомьтесь со ссылками, представленными на главной странице [репозитория с домашними заданиями](../README.md).

**Важно**: если у вас что-то не получилось, оформите Issue согласно [правилам](../report-requirements.md).

## Как сдавать задачи

1. Откройте ваш проект из предыдущего ДЗ.
1. Сделайте необходимые коммиты.
1. Сделайте push. Убедитесь, что ваш код появился на GitHub.
1. Ссылку на ваш проект отправьте в личном кабинете на сайте [netology.ru](https://netology.ru).
1. Задачи, отмеченные как необязательные, можно не сдавать, это не повлияет на получение зачёта. В этом ДЗ все задачи обязательные.

## Задача MVI

### Задача

Вам нужно декомпозировать существующий PostViewModel и перейти на MVI:
* Разделите PostViewModel на 2 вьюмодели - первая для списка постов (FeedViewModel), вторая для создания и редактирования (PostFormViewModel)
* Замените в фрагментах вызовы `by activityViewModels()` на `by viewModels()`, чтобы не использовать механизм SharedViewModel без необходимости
* Сформируйте состояния экрана списка постов и экрана создания/редактирования
    <details>
    <summary>Подсказка</summary>

    FeedState.kt
    ```kotlin
    data class FeedState(
        val posts: List<Post> = emptyList()
    )
    ```
    PostFormState.kt
    ```kotlin
    data class PostFormState(
        val id: Long? = null,
        val content: String = "",
        val emptyError: Boolean = false,
    )
    ```
    </details>
* Создайте Intent в виде sealed interface отдельно для списка постов и для создания/редактирования
* Реализуйте PostFormViewModel аналогично [примеру из лекции](https://github.com/netology-code/and2-code/tree/master/15_mvi/mvi). По нажатию на кнопку сохранения должна быть валидация.
* FeedViewModel должен поддерживать архитектуру mvi
    <details>
    <summary>Подсказка</summary>

    ```kotlin
    class FeedViewModel(application: Application) : AndroidViewModel(application) {

        private val repository = PostRepositoryRoomImpl(
            AppDb.getInstance(application).postDao
        )

        private val _state = MutableLiveData(FeedState())
        val state: LiveData<FeedState> = _state

        init {
            repository.getAll().observeForever { posts ->
                reduce {
                    copy(posts = posts)
                }
            }
        }

        fun sendIntent(intent: FeedIntent) {
            when (intent) {
                TODO("Реализуйте в рамках ДЗ")
            }
        }

        private fun reduce(block: FeedState.() -> FeedState) {
            val current = _state.value ?: FeedState()
            _state.value = block(current)
        }

        override fun onCleared() {
            repository.get().removeObserver(repositoryObserver)
            super.onCleared()
        }
    }
    ```
    </details>

* Обновите код FeedFragment и фрагмента редактирования поста для поддержки mvi

Всё, что работало до этого: лайки, создание и редактирование поста — должно продолжать работать.

Связанный с конкретным экраном код лучше размещать в отдельном пакете, чтобы не получить свалку. 

Возможная структура проекта:
```
├── AppActivity.kt
├── core
│   ├── db
│   │   ├── AppDb.kt
│   │   ├── PostDao.kt
│   │   └── entity/
│   │       └── PostEntity.kt
│   ├── model
│   │   └── Post.kt
│   ├── util
│   │   ├── AndroidUtils.kt
│   │   └── StringArg.kt
│   │
│   └── push
│       └── FCMService.kt
├── data
│   └── repository
│       ├── PostRepository.kt
│       └── PostRepositoryRoomImpl.kt
│
└── feature
    └── post
        ├── list
        │   ├── FeedFragment.kt
        │   ├── FeedViewModel.kt
        │   ├── FeedState.kt
        │   ├── FeedIntent.kt
        │   └── adapter/
        │       └── PostAdapter.kt
        │
        └── form
            ├── PostFormFragment.kt
            ├── PostFormViewModel.kt
            ├── PostFormState.kt
            └── PostFormIntent.kt
```

Опубликуйте изменения в вашем проекте на GitHub в ветке `mvi`. Создайте из этой ветки Pull Request в `main`. Убедитесь, что apk собирается с помощью GitHub Actions и при установке в эмуляторе приложение работает корректно.

В качестве результата пришлите ссылку на ваш Pull Request в личном кабинете студента на сайте [netology.ru](https://netology.ru).

После принятия задачи сделайте `merge` Pull Request c веткой `main`.
