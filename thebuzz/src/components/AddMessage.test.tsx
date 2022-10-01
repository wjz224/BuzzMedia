import React from 'react';
import { cleanup, getByTestId, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import AddMessage from './AddMessage';

afterEach(() => { 
  cleanup();
})

describe("AddMessage Component" , () => {

  test('Render text for add message', () => {
  render(<AddMessage />);
  const linkElement = screen.getByText('Add Message:');
  expect(linkElement).toBeInTheDocument();
  
})
})