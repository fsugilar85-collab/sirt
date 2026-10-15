# Rencana Implementasi: Aplikasi Android si-RT/RW

Aplikasi Android resmi untuk portal layanan **si-RT/RW (Sistem Informasi RT/RW) Desa Jambudipa Kec. Warungkondang** yang terhubung langsung ke `https://sirtrw.blogspot.com/`. Aplikasi ini mengadopsi identitas visual dari gambar yang dilampirkan: ikon aplikasi bertema biru modern dan splash screen khas desa dengan transisi mulus ke portal website.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> Berdasarkan jawaban klarifikasi yang telah Anda pilih:
> - **Transisi Awal**: Tampilan pembuka (Splash Screen) akan tampil selama 2,5 detik lalu otomatis beralih ke portal website.
> - **Kontrol Navigasi**: Dilengkapi bar kontrol elegan di bagian atas/bawah dengan tombol Kembali, Refresh, Maju, dan Beranda (Home) untuk memudahkan warga berselancar di portal.
> - **Nama Aplikasi**: "si-RT/RW" di layar beranda HP (Launcher) dan metadata aplikasi.

- **URL Target**: `https://sirtrw.blogspot.com/`
- **Dukungan Offline**: Layar penanganan ramah saat tidak ada koneksi internet dengan tombol *Coba Lagi* (Retry).
- **Penanganan Tombol Kembali Fisik/Gesture**: Menekan tombol kembali akan memundurkan halaman website terlebih dahulu sebelum keluar dari aplikasi.

---

## 1. Overview & Core Concept

- **Apa yang Dibuat**: Aplikasi Android native berbasis Jetpack Compose yang mengemas portal Sistem Informasi RT/RW secara responsif, cepat, dan terintegrasi dengan tampilan pembuka sesuai Gambar 2 dan ikon sesuai Gambar 1.
- **Pengguna Sasaran**: Warga dan perangkat pengurus RT/RW Desa Jambudipa, Kecamatan Warungkondang untuk mengakses informasi kependudukan, pengumuman, dan layanan administrasi desa.
- **Nilai Utama**: Akses instan satu ketukan dari ponsel tanpa perlu membuka browser secara manual, tampilan resmi terpercaya, dan navigasi yang ramah pengguna.

---

## 2. User Experience & Visual Design

### Alur Antarmuka (User Flows)
1. **Layar Pembuka (Splash / First Launch Screen)**:
   - Sesuai dengan Gambar 2: Latar belakang pemandangan alam Desa Jambudipa yang asri dengan sawah hijau dan gedung balai warga berhias merah putih.
   - Logo ikon rumah putih dengan siluet keluarga dan dokumen layanan.
   - Tipografi tebal judul **si-RT/RW**.
   - Badge pill biru muda bersudut tumpul: **SISTEM INFORMASI RT/RW**.
   - Keterangan lokasi: **Desa Jambudipa Kec. Warungkondang**.
   - Indikator halus pemuatan sebelum berpindah otomatis setelah 2,5 detik (dengan opsi sentuh layar untuk langsung melompat masuk).

2. **Layar Utama (Portal Web si-RT/RW)**:
   - Header bar modern bernuansa biru terpercaya (`#0077C8` / `#0288D1`) dengan tombol aksi:
     - 🏠 **Beranda**: Kembali ke halaman utama `https://sirtrw.blogspot.com/`
     - ◀️ **Kembali**: Mundur ke halaman web sebelumnya
     - ▶️ **Maju**: Maju ke halaman web berikutnya
     - 🔄 **Refresh**: Memuat ulang halaman
   - Linear Progress Bar tipis di bawah header yang menunjukkan status loading halaman web secara real-time.
   - WebView optimal dengan dukungan JavaScript, DOM Storage, zoom, dan pengunduhan file/tautan formulir jika diperlukan.

3. **Status Kesalahan / Tanpa Internet (Error Handling)**:
   - Jika koneksi terputus atau gagal memuat, aplikasi tidak menampilkan layar putih error browser, melainkan kartu bantuan berdesain rapi dengan ikon sinyal dan tombol "Muat Ulang Halaman".

### Identitas Visual & Ikon Aplikasi (Gambar 1)
- **App Launcher Icon**: Menggunakan aset logo Gambar 1 dengan gradien cyan ke deep blue (`#00C0F0` ke `#0052D4`), siluet rumah, warga, dan berkas surat, serta teks **si-RT/RW** yang disesuaikan dalam standar Adaptive Icon Android (foreground & background layer + density mipmap).
- **Warna & Tema**:
  - Primary: `#0077C8` (Biru Desa Informatif)
  - Secondary / Accent: `#00C0F0` (Cyan Modern)
  - Pill Badge: `#4DA8DA` dengan teks putih kontras
  - Background Splash: Foto panorama pemandangan Desa Jambudipa

---

## 3. Keputusan Produk & Arsitektur Teknis

- **Pendekatan Tampilan**: Menggunakan Jetpack Compose dengan `AndroidView` untuk hosting `WebView` berperforma tinggi, digabungkan dengan state Compose (`rememberWebViewClient`, status navigasi, dan progress).
- **Penanganan Back Button**: Integrasi `BackHandler` dari Jetpack Compose. Jika riwayat web ada (`canGoBack`), akan kembali ke halaman web sebelumnya. Jika sudah di halaman utama, ketuk dua kali atau dialog konfirmasi untuk keluar.
- **Konfigurasi WebView Modern**:
  - `javaScriptEnabled = true`
  - `domStorageEnabled = true` (mendukung fitur widget Blogger dan formulir interaktif)
  - `setSupportZoom(true)` dan `builtInZoomControls = true` (dengan `displayZoomControls = false` agar bersih)
  - Penanganan tautan eksternal (misal nomor WhatsApp perangkat desa / link telpon `tel:` atau `mailto:` terbuka di aplikasi yang sesuai).

---

## 4. Diagram Arsitektur & Komponen

```
┌─────────────────────────────────────────────────────────────────┐
│                       Aplikasi si-RT/RW                         │
└────────────────────────────────┬────────────────────────────────┘
                                 │
           ┌─────────────────────┴─────────────────────┐
           ▼                                           ▼
┌─────────────────────────────┐             ┌─────────────────────────────┐
│      1. SplashScreen        │             │       2. MainScreen         │
│  - Latar Balai Desa         │  2.5 detik  │  - Top AppBar:              │
│  - Logo Rumah & Warga       ├────────────►│    [Home] [Back] [Refresh]  │
│  - "si-RT/RW"               │             │  - Linear Loading Bar       │
│  - Badge SISTEM INFORMASI   │             │  - Interactive WebView      │
│  - Teks Desa Jambudipa      │             │  - Offline / Retry State    │
└─────────────────────────────┘             └─────────────────────────────┘
```

---

## 5. Rencana Langkah Pengerjaan

1. **Metadata & Izin**:
   - Menambahkan izin `<uses-permission android:name="android.permission.INTERNET" />` dan `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />` di `AndroidManifest.xml`.
   - Mengatur nama aplikasi `si-RT/RW` di `strings.xml` dan `metadata.json`.
2. **Aset Visual & Icon Launcher**:
   - Menyiapkan aset latar belakang Splash Screen dari Gambar 2.
   - Menyiapkan aset ikon launcher dari Gambar 1 (adaptive icon + mipmap).
3. **Komponen UI Compose**:
   - Membuat `SplashScreen.kt` yang mereplikasi tampilan Gambar 2 secara presisi (latar belakang, logo, badge rounded, tipografi).
   - Membuat `WebViewScreen.kt` dengan top bar navigasi lengkap, progress loading, dan penanganan error.
   - Menghubungkan transisi dengan state navigation atau animated switcher di `MainActivity.kt`.
4. **Kompilasi & Verifikasi**:
   - Menjalankan `compile_applet` untuk memastikan build Gradle sukses tanpa error.
