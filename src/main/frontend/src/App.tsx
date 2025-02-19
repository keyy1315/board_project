import Header from "./component/Header";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Footer from "./component/Footer";
import "./css/style.css";
import { RecoilRoot } from "recoil";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import SideBar from "./component/SideBar";
import BoardList from "./pages/BoardList";
import Board from "./pages/Board";
import BoardWrite from "./pages/BoardWrite";

const queryClient = new QueryClient();
function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen={false} />
      <RecoilRoot>
        <BrowserRouter>
          <Header />
          <Routes>
            <Route path="/" element={<SideBar />}>
              <Route index element={<BoardList />} />
              <Route path="board/:no" element={<Board />} />
              <Route path="board/write" element={<BoardWrite />}/>
            </Route>
          </Routes>
          <Footer />
        </BrowserRouter>
      </RecoilRoot>
    </QueryClientProvider>
  );
}

export default App;
