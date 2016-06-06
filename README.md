# GDProject
Test project for GD/ TaskMaster

2 Activities
1 - with list and possibility to delete/edit tasks
2 - place to edit/add tasks


`1 Activity`
- Short click on list item - DialogFragment with extended information about item.
- Long click on list item - DialogFragment with possibilities to edit or delete item.
- All images are download asynchronous with Picasso. (or with Volley, Volley code is commented)
  - `Settings`
  - In settings user can sort list ascending or by date.
  - Import DB to JSON.
  - Export DB from JSON, if there is no DB to export user will get error message.

`2 Activity`
- Fields for edit or create new task.
- Depends on action (edit/delete) button change its picture so user knows what he is doing.
- Title field is required and unique, if there is already title like that in DB or there is no title user will see information about error.


Generated program is on GitHub too - `app-release.apk`