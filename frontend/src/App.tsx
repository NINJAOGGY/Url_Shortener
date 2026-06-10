import CreateUrlForm from "./components/CreateUrlForm";
import UrlStats from "./components/UrlStats";
import DeleteUrl from "./components/DeleteUrl";

function App() {
  return (
    <div className="min-h-screen bg-gray-100 p-8">

      <div className="max-w-5xl mx-auto">

        <h1 className="text-4xl font-bold text-center mb-8">
          URL Shortener
        </h1>

        <div className="grid gap-6 md:grid-cols-2">

          <CreateUrlForm />

          <UrlStats />

        </div>

        <div className="mt-6">
          <DeleteUrl />
        </div>

      </div>

    </div>
  );
}

export default App;