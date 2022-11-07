import { cleanup, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import CommentPage from './CommentPage';

//Testing file for AddMessage.tsx

//Clears buffer after each test
afterEach(() => { 
  cleanup();
})

//1 test included in below description
describe("AddMessage Component" , () => {

  //Test 1: lokking for the text "Add Message"
  test('Render text for add message', () => {
  render(<CommentPage />);
  const linkElement = screen.getByText('Comments');
  expect(linkElement).toBeInTheDocument();
  
})
})