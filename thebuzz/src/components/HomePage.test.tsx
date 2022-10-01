import React from 'react';
import { cleanup, getByTestId, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"

afterEach(() => { 
  cleanup();
})

describe("Homepage Component" , () => {

  test("Like Button Rendering", () => {
    expect(screen.queryByTestId('likebtn')).toBeEnabled()
  })

  test("Like Button Text", () => {
    expect(screen.queryByTestId('likebtn')).toHaveTextContent("Like")
  })

  test("Delete Button Rendering", () => {
    expect(screen.queryByTestId('deletebtn')).toBeEnabled()
  })

  test("Delete Button Text", () => {
    expect(screen.queryByTestId('deletebtn')).toHaveTextContent("Delete")
  })
  
})