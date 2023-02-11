# User REST API

# Features

1. Login
2. Register
3. Get User
4. Update User
5. Delete User

# Catatan

- Pertama, kita lakukan Register User
- Kedua, kita melakukan Login
- Setelah login kita bisa melakukan Update User
- Sedangkan untuk GET User bisa dilakukan tanpa login (alias kita set permit all di Security Config)

# Format Response

1. Registrasi sukses, berikan response Http Status Code 201, pesan Sukses registrasi
2. Registrasi Gagal, username sudah ada di database berikan response Http Status Code 409 dengan pesan Username sudah terpakai 
3. Sukses Login, berikan response Http Status Code 200, dengan pesan Sukses Login 
4. Gagal Login, username dan atau password kosong berikan response Http Status Code 400, dengan pesan "Username dan atau Password kosong"
5. Gagal login, password tidak cocok dengan username, berikan response Http Status Code 401, dengan pesan "Password tidak cocok dengan username"
6. Get List User mengambil semua data user
7. Update User, jika sukses maka response Http Status Code 201, pesan "Sukses update"
8. Gagal Update, jika username sudah ada didatabase, berikan Http Status Code 409, pesan "Username sudah terpakai"
9. Gagal Update, jika password baru sama dengan password lama, berikan Http Status 400, dengan pesan "Password tidak boleh sama dengan password sebelumnya"

# Format Response tanpa ada data

```json
{
  "code": "",
  "success": "",
  "message": ""
}
```

# Format Response jika ada data(body)

```json
{
  "code": "",
  "success": "",
  "message": "",
  "body": []
}
```